package ar.edu.unlp.sedici.oaiSimple;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiErrorException;
import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiRuntimeException;
import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiRuntimeException.ExceptionCause;
import ar.edu.unlp.sedici.oaiSimple.model.GranularityType;
import ar.edu.unlp.sedici.oaiSimple.model.IdentifyDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.GetRecordRequest;
import ar.edu.unlp.sedici.oaiSimple.requests.IdentifyRequest;
import ar.edu.unlp.sedici.oaiSimple.requests.ListIdentifiersRequest;
import ar.edu.unlp.sedici.oaiSimple.requests.ListMetadataFormatsRequest;
import ar.edu.unlp.sedici.oaiSimple.requests.ListRecordsRequest;
import ar.edu.unlp.sedici.oaiSimple.requests.ListSetsRequest;
import ar.edu.unlp.sedici.oaiSimple.requests.VerbRequest;
import ar.edu.unlp.sedici.oaiSimple.responses.GetRecordResult;
import ar.edu.unlp.sedici.oaiSimple.responses.IdentifyResult;
import ar.edu.unlp.sedici.oaiSimple.responses.ListIdentifiersResult;
import ar.edu.unlp.sedici.oaiSimple.responses.ListMetadataFormatsResult;
import ar.edu.unlp.sedici.oaiSimple.responses.ListRecordsResult;
import ar.edu.unlp.sedici.oaiSimple.responses.ListSetsResult;
import ar.edu.unlp.sedici.oaiSimple.responses.VerbResult;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;
import ar.edu.unlp.sedici.xmlutils.XmlResource;

public class OaiPmh2 {

	private String baseUrl;
	private GranularityType granularity;
	
	private int retry_after_count = 20;
	//numero de ms que se pueden utilizar para establecer las conexiones con el servidor, en caso 
	//que este tiempo pase, se cancela la conexion. Si connection_timeout == -1, entonces no define 
	//y la virtual machina decidira el tiempo maximo 
	private int connection_timeout = 300000; //5 minutos 
	private String user_agent = "CelsiusHarvester";
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
////////////////////////////////////////////////
////////////////////////////////////////////////
////////////////////////////////////////////////
	
	
	
	public OaiPmh2(String baseUrl, GranularityType granularity) {
		this.baseUrl = baseUrl;
		this.granularity = granularity;
	}
	public OaiPmh2(String baseUrl) {
		this.baseUrl = baseUrl;
		this.granularity = GranularityType.YYYYMMDD;
	}
	
	public IdentifyResult identify() throws OaiErrorException, OaiRuntimeException {
		return this.performRequestAndThrowErrors(new IdentifyRequest());
	}
	
	public ListRecordsResult listRecords(String resumptionToken) throws OaiErrorException, OaiRuntimeException {
		return this.performRequestAndThrowErrors(new ListRecordsRequest(resumptionToken));
	}
	
	public ListRecordsResult listRecords(Date from, Date until, String set, String metadataPrefix) throws OaiErrorException, OaiRuntimeException {
		return this.performRequestAndThrowErrors(new ListRecordsRequest(from, until, set, metadataPrefix, this.granularity));
	}
	
	public ListIdentifiersResult listIdentifiers(Date from, Date until, String set, String metadataPrefix) throws OaiErrorException, OaiRuntimeException {
		return this.performRequestAndThrowErrors(new ListIdentifiersRequest(from, until, set, metadataPrefix, this.granularity));
	}

	public ListSetsResult listSets() throws OaiErrorException, OaiRuntimeException {
		return this.performRequestAndThrowErrors(new ListSetsRequest());
	}
	
	public ListMetadataFormatsResult listMetadataFormats() throws OaiErrorException, OaiRuntimeException {
		return this.performRequestAndThrowErrors(new ListMetadataFormatsRequest());
	}
	
	public GetRecordResult getRecord(String record, String metadataPrefix) throws OaiErrorException, OaiRuntimeException {
		return this.performRequestAndThrowErrors(new GetRecordRequest(record,metadataPrefix));
	}
	

////////////////////////////////////////////////
////////////////////////////////////////////////
////////////////////////////////////////////////

	/**
	 * Ejecuta el VerbRequest y hace un trhow OaiErrorException si el servidor retorno algun error
	 */
	private <T extends VerbResult> T performRequestAndThrowErrors(VerbRequest<T> request) throws OaiErrorException, OaiRuntimeException {
		T result = this.performRequest(request);
		try {
			result.throwErrors();
		} catch (IOException e) {
			throw new OaiRuntimeException(ExceptionCause.io_error, e.toString(), e);
		} catch (XmlProcessingException e) {
			throw new OaiRuntimeException(ExceptionCause.malformed_xml, "Error al procesar el xml: "+e.getMessage(), e);
		}
		return result;
	}
	
	static <T extends VerbResult> String buildURL(String baseUrl, VerbRequest<T> request){
		StringBuffer requestURL = new StringBuffer();
		requestURL.append("?").append(VerbRequest.PARAM_verb).append("=").append(request.getVerb());
		Map<String, String > params = request.getRequestParams();
		for (Entry<String,String> param: params.entrySet()) {
			requestURL.append("&").append(param.getKey()).append("=").append(param.getValue());
		}
		String arguments = requestURL.toString();
		return baseUrl+= arguments;
		
	}
	private <T extends VerbResult> T performRequest(VerbRequest<T> request) throws OaiRuntimeException{
		String requestURLString = buildURL(this.baseUrl, request);
		
		//saco el encoding de la url por ahora
//		try {
//			requestURLString+= URLEncoder.encode(arguments , "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			requestURLString+=URLEncoder.encode(arguments ) ;
//		}
//		
		log.info("Requesting to "+requestURLString);
		
		InputStream dataStream = null;
		try {
			dataStream = this.connect(requestURLString);
		} catch (SocketTimeoutException e) {
			throw new OaiRuntimeException(ExceptionCause.connection_timeout, "Timeout en la conexion con la URL "+requestURLString,e);
		} catch(UnknownHostException e1) {
			throw new OaiRuntimeException(ExceptionCause.server_not_found, "No se encuentra el host "+requestURLString, e1);
		} catch(SocketException e1) {
			//Las SocketException se deben a "Connection reset"/"Unexpected end of file from server"
			//Las ConnectException se deben a "Connection timed out"/"Connection refused"
			throw new OaiRuntimeException(ExceptionCause.connection_error, "Oucrrió un error con la conexion al servidor: "+e1.getMessage(), e1);
		} catch(FileNotFoundException e1) {
			//La FileNotFoundException es disparada por el HarvesterVerb para denotar una BadURL
			throw new OaiRuntimeException(ExceptionCause.server_not_found, "No se encuentra al host del la url "+requestURLString, e1);
		} catch (IOException e1) {
			//Todas las IOException se generaron por un codigo 500/502 en la respuesta
			throw new OaiRuntimeException(ExceptionCause.connection_error, "Error en la respuesta del servidor: "+e1.getMessage(), e1);
		}
	
		XmlResource resource;
		try {
			resource  = new XmlResource(dataStream);
		} catch (IOException e) {
			throw new OaiRuntimeException(ExceptionCause.io_error, e.toString(), e);
		} catch (XmlProcessingException e) {
			throw new OaiRuntimeException(ExceptionCause.malformed_xml, "Error al procesar el xml: "+e.getMessage(), e);
		}
		
		return request.getResponseInstance(resource);
	}
	
	/**
	 * 
	 * @param requestURL
	 * @return 
	 * @throws IOException 
	 * @throws FileNotFoundException
	 * @throws MalformedURLException
	 */
	private InputStream connect(String requestURL) throws IOException{

		InputStream in = null;
		
		
		URL url = new URL(requestURL);	
		HttpURLConnection con = null;
		int responseCode = 0;
		int retriedCount = 0;
		
		do {
			con = (HttpURLConnection) url.openConnection();
			
			//Se maneja el timeout en las conexiones             
            con.setConnectTimeout(this.connection_timeout);
            con.setReadTimeout(this.connection_timeout);
            con.setRequestProperty("User-Agent", this.user_agent);
			con.setRequestProperty("Accept-Encoding", "compress, gzip, identify");
			
			try {
				responseCode = con.getResponseCode();
				//log.trace("Connection Established with '{}'. ResponseCode: {}", requestURL, responseCode);
			} catch (FileNotFoundException e) {
				// assume it's a 503 response
				//log.debug("Cannot establish connection  with '{}'. Error message: {}", requestURL, e.getMessage());
				responseCode = HttpURLConnection.HTTP_UNAVAILABLE;
			}
			log.trace("Connection with '{}'. ResponseCode: {}", requestURL, responseCode);
			
			if (responseCode == HttpURLConnection.HTTP_UNAVAILABLE) {
				long retrySeconds = con.getHeaderFieldInt("Retry-After", -1);
				if (retrySeconds == -1) {
					long now = (new Date()).getTime();
					long retryDate = con.getHeaderFieldDate("Retry-After", now);
					retrySeconds = retryDate - now;
				}
				if (retrySeconds == 0) { // Apparently, it's a bad URL
					log.info("Cannot establish connection  with '{}', maybe a bad URL?. Server Returned response code 503", requestURL);
					throw new FileNotFoundException("Cannot connect to "+requestURL+", server returned 503");
				}
				
				if (retriedCount> this.retry_after_count ) { // Apparently, it's a bad URL
					log.info("Cannot establish connection  with '{}', Max retry-after ({}) reached", requestURL,this.retry_after_count);
					throw new FileNotFoundException("Max retry-after ("+this.retry_after_count+") reached ");
				}
				
				log.trace("Server {} response: Retry-After={}",requestURL ,retrySeconds);
				
				if (retrySeconds > 0) {
					retriedCount++;
					try {
						long t = retrySeconds * 1000 * (1 + this.retry_after_count - (this.retry_after_count/ retriedCount ));
						log.info("El thread duerme por {} milisegundos por un Retry-After sobre {}",t,url.getHost());
						Thread.sleep(t);
					} catch (InterruptedException ex) {
						throw new InterruptedIOException();
					}
				}
			}
		} while (responseCode == HttpURLConnection.HTTP_UNAVAILABLE);
		
		String contentEncoding = con.getHeaderField("Content-Encoding");
		log.debug("contentEncoding={}", contentEncoding);
		
		if ("compress".equals(contentEncoding)) {
			ZipInputStream zis = new ZipInputStream(con.getInputStream());
			zis.getNextEntry();
			in = zis;
		} else if ("gzip".equals(contentEncoding)) {
			in = new GZIPInputStream(con.getInputStream());
		} else if ("deflate".equals(contentEncoding)) {
			in = new InflaterInputStream(con.getInputStream());
		} else {
			in = con.getInputStream();
		}
		return in;
	}
	
////////////////////////////////////////////////
////////////////////////////////////////////////
////////////////////////////////////////////////
		
	protected static void main(String[] args) throws OaiErrorException, OaiRuntimeException, XmlProcessingException, IOException {
		OaiPmh2 sediciOai = new OaiPmh2("http://sedici.unlp.edu.ar/phpoai/oai2.php", GranularityType.YYYYMMDD);
		IdentifyResult res = sediciOai.identify();
		IdentifyDefinition def = res.getDefinition();
		System.out.println("RepositoryName=" +def.getRepositoryName());
		//sediciOai.listRecords(null, null, null, "oai_dc")
	}
}

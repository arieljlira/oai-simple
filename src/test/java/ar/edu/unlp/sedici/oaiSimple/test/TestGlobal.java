package ar.edu.unlp.sedici.oaiSimple.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ar.edu.unlp.sedici.oaiSimple.OaiPmh2;
import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiErrorException;
import ar.edu.unlp.sedici.oaiSimple.exceptions.OaiRuntimeException;
import ar.edu.unlp.sedici.oaiSimple.model.IdentifyDefinition;
import ar.edu.unlp.sedici.oaiSimple.model.MetadataFormatDefinition;
import ar.edu.unlp.sedici.oaiSimple.requests.ListRecordsRequest;
import ar.edu.unlp.sedici.oaiSimple.responses.IdentifyResult;
import ar.edu.unlp.sedici.oaiSimple.responses.ListRecordsResult;
import ar.edu.unlp.sedici.xmlutils.XmlProcessingException;

public class TestGlobal {

	public static void main(String[] args) throws OaiErrorException, OaiRuntimeException, XmlProcessingException, IOException {
		//List<String> urls =  new LinkedList<String>();
		//urls.add("http://sedici.unlp.edu.ar/phpoai/oai2.php");
		new TestGlobal().processRepositories(getRepositoriesList222());
	}
	
	
	
	
	private void processRepositories(List<String> urls)  {
		for (String baseUrl : urls) {
			try {
				processRepository(new OaiPmh2(baseUrl));
			} catch (Exception e) {
				System.out.println("Se corta el procesamiento del repositorio con id ["+baseUrl+"] dado que genero la siguiente excepcion: "+ e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void processRepository(OaiPmh2 oai) throws OaiErrorException, OaiRuntimeException, XmlProcessingException, IOException{	
		saveIdentify(oai.identify().getDefinition());
		
		List<MetadataFormatDefinition> mfd = oai.listMetadataFormats().getMetadataFormats();
		saveListMetadataFormats(mfd);
		for (MetadataFormatDefinition metadataFormatDefinition : mfd) {
			ListRecordsResult lr = oai.listRecords(null, null, null, metadataFormatDefinition.getMetadataPrefix());
			this.saveListRecords(lr);
		}
	}

	private void saveIdentify(IdentifyDefinition definition) {
		System.out.println("Proceso el repositorio "+definition.getRepositoryName());
	}

	private void saveListMetadataFormats(List<MetadataFormatDefinition> mfd){
		for (MetadataFormatDefinition metadataFormatDefinition : mfd) {
			System.out.println(metadataFormatDefinition.getMetadataPrefix());
		}
	}

	private void saveListRecords(ListRecordsResult lr) throws XmlProcessingException, IOException{
		System.out.println("Recupero "+lr.getRecordCount()+" para el prefix "+((ListRecordsRequest)lr.getRequest()).getMetadataPrefix());
		//lr.getXmlResource();
	}
	

	private static List<String> getRepositoriesList222(){
		List<String> urls =  new LinkedList<String>();
		
		urls.add("http://dspace.library.uu.nl:8080/dspace-oai/request");
		urls.add("http://www.dspace.cam.ac.uk/dspace-oai/request");
		urls.add("http://drc.ohiolink.edu/oai/request");
		urls.add("http://www2.senado.gov.br/bdsf-oai/request");
		urls.add("http://drc.libraries.uc.edu/oai/request");
		urls.add("http://gredos.usal.es/oai/request");
		urls.add("http://air.unimi.it/dspace-oai/request");
		urls.add("http://orbi.ulg.ac.be/oai/request");
		urls.add("http://strip1.oit.umn.edu:8080/dspace_sr-oai/request");
		urls.add("http://strip1.oit.umn.edu:8080/dspace_ir-oai/request");
		urls.add("http://dare.ubn.ru.nl/dspace-oai/request");
		urls.add("http://bibliotecadigital.icesi.edu.co/biblioteca_digital-oai/request");
		urls.add("http://deepblue.lib.umich.edu/dspace-oai/request");
		urls.add("http://dspace.vsb.cz/dspace-oai/request");
		urls.add("http://dspace.mit.edu/oai/request");
		urls.add("http://repository.tamu.edu/dspace-oai/request");
		urls.add("https://kb.osu.edu/dspace-oai/request");
		urls.add("http://agspace.nal.usda.gov:8080/dspace-oai/request");
		urls.add("https://idl-bnc.idrc.ca/dspace-oai/request");
		urls.add("http://digital.csic.es/dspace-oai/request");
		urls.add("http://www.infoteca.cnptia.embrapa.br/oai/request");
		urls.add("http://www.econstor.eu/dspace-oai/request");
		urls.add("http://documents.irevues.inist.fr/dspace-oai/request");
		urls.add("http://bdjur.stj.gov.br/dspace-oai/request");
		urls.add("http://smartech.gatech.edu/dspace-oai/request");
		urls.add("http://rudr.rice.edu/dspace-oai/request");
		urls.add("http://oai.library.jhu.edu/oai-dspace/request");
		urls.add("http://dspace.iss.it/dspace-oai/request");
		urls.add("http://tspace.library.utoronto.ca/tspace-oai/request");
		urls.add("http://oai.recercat.net/request");
		urls.add("http://dml.cz/dspace-oai/request");
		urls.add("http://dspace.nbuv.gov.ua:8080/dspace-oai/request");
		urls.add("https://dspace.ndlr.ie/oai/request");
		urls.add("http://www.alice.cnptia.embrapa.br/oai/request");
		urls.add("http://helda.helsinki.fi/oai/request");
		urls.add("http://www.ideals.illinois.edu/dspace-oai/request");
		urls.add("http://ir.library.oregonstate.edu/oai/request");
		urls.add("http://dspace.hil.unb.ca:8080/oai/request");
		urls.add("http://essuir.sumdu.edu.ua/oai/request");
		urls.add("http://gupea.ub.gu.se/dspace-oai/request");
		urls.add("http://www.saber.ula.ve/dspace-oai/request");
		urls.add("http://dare.ubvu.vu.nl/oai/request");
		urls.add("http://www.tiedekirjasto.helsinki.fi/dspace-oai/request");
		urls.add("http://www.lume.ufrgs.br/dspace-oai/request");
		urls.add("http://dspace.utlib.ee/dspace-oai/request");
		urls.add("http://dspace.upce.cz/oai/request");
		urls.add("http://www.kspace.org/dspace-oai/request");
		urls.add("http://repositorio-aberto.up.pt/oaiextended/request");
		urls.add("http://evols.library.manoa.hawaii.edu/dspace-oai/request");
		urls.add("http://www.dspace.espol.edu.ec/oai/request");
		urls.add("http://art.torvergata.it/dspace-oai/request");
		urls.add("http://rua.ua.es/dspace-oai/request");
		urls.add("http://digibug.ugr.es/oai/request");
		urls.add("http://jyx.jyu.fi/dspace-oai/request");
		urls.add("http://openaccess.leidenuniv.nl/dspace-oai/request");
		urls.add("http://open.jorum.ac.uk/oai/request");
		urls.add("http://scholarspace.manoa.hawaii.edu/dspace-oai/request");
		urls.add("http://si-pddr.si.edu/dspace-oai/request");
		urls.add("http://repositorium.sdum.uminho.pt/oai/oai");
		urls.add("http://www.captura.uchile.cl:8080/oai/request");
		urls.add("http://repositories.lib.utexas.edu/oai/request");
		urls.add("http://krex.k-state.edu/dspace-oai/request");
		urls.add("http://dspace.lib.uom.gr/dspace-oai/request");
		urls.add("http://digitum.um.es/dspace-oai/request");
		urls.add("http://cdigital.uv.mx/oai/request");
		urls.add("http://dspace.bangor.ac.uk/dspace-oai/request");
		urls.add("http://tdx.cat/oai/request");
		urls.add("http://drum.lib.umd.edu/oai-pmh/");
		urls.add("http://repositori.udl.cat/oaiextended/request");
		urls.add("http://dlynx.rhodes.edu/oai/request");
		urls.add("http://cadmus.eui.eu/oai/request");
		urls.add("http://ritdml.rit.edu/dspace-oai/request");
		urls.add("http://estudogeral.sib.uc.pt/oaiextended/request");
		urls.add("http://doclib.uhasselt.be/dspace-oai/request");
		urls.add("http://e-archivo.uc3m.es:8080/dspace-oai/request");
		urls.add("http://www.ruor.uottawa.ca/dspace-oai/request");
		urls.add("http://ena.lp.edu.ua:8080/oai/request");
		urls.add("http://dspace.udel.edu:8080/dspace-oai/request");
		urls.add("http://dugifonsespecials.udg.edu/dspace-oai/request");
		urls.add("http://dspace.univer.kharkov.ua/oai/request");
		urls.add("http://www.dspace.univer.kharkov.ua/oai/request");
		urls.add("https://www.library.yorku.ca/dspace-oai/request");
		urls.add("http://eprints.upc.es:8080/pfc-oai/request");
		urls.add("http://dspace.mah.se/dspace-oai/request");
		urls.add("http://riunet.upv.es/oai/request");
		urls.add("http://eprints.upc.es:8080/revistes-oai/request");
		urls.add("http://aladinrc.wrlc.org/dspace-oai/request");
		urls.add("http://dspace.bib.hb.se/dspace-oai/request");
		urls.add("http://repositorio.bce.unb.br/riunb-oai/request");
		urls.add("http://148.226.9.79:8080/dspace-oai/request");
		urls.add("https://dspace.lboro.ac.uk/dspace-oai/request");
		urls.add("http://roderic.uv.es/oai/request");
		urls.add("http://virtualbib.fgv.br/oai/request");
		urls.add("http://scholarworks.iu.edu/dspace-oai/request");
		urls.add("http://dspace.lib.ncsu.edu:8080/dr-oai/request");
		urls.add("http://dspace.utalca.cl/dspace-oai/request");
		urls.add("http://basepub.dauphine.fr/oai/request");
		urls.add("http://kuscholarworks.ku.edu/dspace-oai/request");
		urls.add("http://aisberg.unibg.it/dspace-oai/request");
		urls.add("http://spiral.imperial.ac.uk/spiral-pub-oai/request");
		urls.add("http://eprints.upc.es:8080/dspace-oai/request");
		urls.add("http://oai-repositori.upf.edu/request");
		urls.add("http://dspace.uabs.edu.ua/oai/request");
		urls.add("http://dash.harvard.edu/oai/request");
		urls.add("http://repository.lib.ncsu.edu/ir/oai/request");
		urls.add("http://cadair.aber.ac.uk/dspace-oai/request");
		urls.add("http://lra.le.ac.uk/dspace-oai/request");
		urls.add("http://www.bvcooperacion.pe/biblioteca-oai/request");
		urls.add("http://dspace.c3sl.ufpr.br/dspace-oai/request");
		urls.add("http://digilib.gmu.edu:8080/dspace-oai/request");
		urls.add("http://repository.unm.edu/dspace-oai/request");
		urls.add("http://repositorio.ucp.pt/oaiextended/request");
		urls.add("http://diggy.ruc.dk/dspace-oai/request");
		urls.add("http://helvia.uco.es/oai/request");
		urls.add("http://ruc.udc.es/oai/request");
		urls.add("http://digitallibrary.amnh.org/dspace-oai/request");
		urls.add("http://repositori.uji.es/oai");
		urls.add("http://papyrus.bib.umontreal.ca/dspace-oai/request");
		urls.add("http://dspace.itg.be/dspace-oai/request");
		urls.add("http://bibliotecadigital.ipb.pt/oaiextended/request");
		urls.add("http://qspace.library.queensu.ca/dspace-oai/request");
		urls.add("http://bura.brunel.ac.uk/dspace-oai/request");
		urls.add("http://digital.lib.washington.edu/dspace-oai/request");
		urls.add("http://diposit.ub.edu/dspace-oai/request");
		urls.add("http://www.era.lib.ed.ac.uk/dspace-oai/request");
		urls.add("https://www.dora.dmu.ac.uk/dspace-oai/request");
		urls.add("http://www.openstarts.units.it/dspace-oai/request");
		urls.add("http://rabida.uhu.es/dspace-oai/request");
		urls.add("http://darchive.mblwhoilibrary.org:8080/oai/request");
		urls.add("http://www.uwspace.uwaterloo.ca/dspace-oai/request");
		urls.add("http://open-pub.iasma.it/dspace-oai/request");
		urls.add("https://bora.uib.no/dspace-oai/request");
		urls.add("https://bora.uib.no/dspace-oai/request");
		urls.add("http://uhra.herts.ac.uk/dspace-oai/request");
		urls.add("http://nemertes.lis.upatras.gr/dspace-oai/request");
		urls.add("http://helios-eie.ekt.gr/oai-driver/request");
		urls.add("http://repositorio.ul.pt/oaiextended/request");
		urls.add("http://repository.library.csuci.edu/dspace-oai/request");
		urls.add("http://bibdigital.epn.edu.ec/oai/request");
		urls.add("http://repositorio.espe.edu.ec/oai/request");
		urls.add("http://acceda.ulpgc.es/oai/request");
		urls.add("http://discovery.dundee.ac.uk/oai/request");
		urls.add("http://dspace.lib.cranfield.ac.uk/dspace-oai/request");
		urls.add("http://repositorio.puce.edu.ec/oai/request");
		urls.add("http://juwel.fz-juelich.de:8080/dspace-oai122fzj/request");
		urls.add("http://dspace.lib.ntua.gr/dspace-oai/request");
		urls.add("http://dspace.ou.nl/dspace-oai/request");
		urls.add("http://www.oceandocs.org/odin-oai/request");
		urls.add("http://run.unl.pt/oai/request");
		urls.add("http://elar.usu.ru/dspace-oai/request");
		urls.add("http://dspace.nyu.edu/request");
		urls.add("http://www.ub.uit.no/munin-oai/request");
		urls.add("http://bengal.indstate.edu/oai/request");
		urls.add("http://www.repository.utl.pt/oai/request");
		urls.add("http://openaccess.uoc.edu/webapps/dspace_rei_oai/request");
		urls.add("http://library.panteion.gr:8080/dspace-oai/request");
		urls.add("http://drc.library.bgsu.edu/oai/request");
		urls.add("http://soar.wichita.edu/dspace-oai/request");
		urls.add("http://dspace.usc.es/oai/request");
		urls.add("https://depot.erudit.org/dspace-oai/request");
		urls.add("http://dugi-doc.udg.edu/dspace-oai/request");
		urls.add("http://www.memoriadigitalvasca.es/dspace-oai/request");
		urls.add("http://repositorio.iaen.edu.ec/oai/request");
		urls.add("http://dspace.fct.unl.pt/oai/request");
		urls.add("http://www.flacsoandes.org/oai/request");
		urls.add("http://irserver.ucd.ie/dspace_oai/request");
		urls.add("http://ourspace.uregina.ca/dspace-oai/request");
		urls.add("http://repository.uwic.ac.uk/dspace-oai/request");
		urls.add("https://dspace.library.uvic.ca:8443/dspace-oai/request");
		urls.add("http://bibliotecadigital.univalle.edu.co/oai/request");
		urls.add("http://oai.bibliothek.uni-kassel.de/dspace-oai/request");
		urls.add("http://repositorio-iul.iscte.pt/oaiextended/request");
		urls.add("http://repositorio.uasb.edu.ec/oai/request");
		urls.add("http://bradscholars.brad.ac.uk/dspace-oai/request");
		urls.add("http://ateneo.unmsm.edu.pe/ateneo/oai");
		urls.add("http://dspace.stir.ac.uk/dspace-oai/request");
		urls.add("http://dl.nsf.ac.lk/oai/request");
		urls.add("http://elar.uniyar.ac.ru/oai/request");
		urls.add("https://research.wsulibs.wsu.edu:8443/dspace-oai/request");
		urls.add("http://repositorio.bib.upct.es/dspace-oai/request");
		urls.add("http://arca.icict.fiocruz.br/oai/request");
		urls.add("http://www.bdae.org.br/dspace-oai/request");
		urls.add("http://dspace.unitus.it/dspace-oai/request");
		urls.add("http://lear.unive.it/dspace-oai/request");
		urls.add("http://openarchive.cbs.dk/oai/request");
		urls.add("http://eureka.lib.teithe.gr:8080/oai/request");
		urls.add("http://repository.ibss.org.ua/dspace-oai/request");
		urls.add("http://travesia.mcu.es/portalnb/oai/request");
		urls.add("http://riuma.uma.es/oai/request");
		urls.add("http://academicarchive.snhu.edu/oai/request");
		urls.add("http://elib.sfu-kras.ru/oai/request");
		urls.add("http://diobma.udg.edu/dspace-oai/request");
		urls.add("http://oai.mdx.cat/request");
		urls.add("http://exeter.openrepository.com/exeter-oai/request");
		urls.add("http://bdm.bce.unb.br/dspace-oai/request");
		urls.add("http://rose.bris.ac.uk/dspace-oai/request");
		urls.add("http://dspace.uevora.pt/otic-oai/request");
		urls.add("https://bdigital.ufp.pt/dspace-oai/request");
		urls.add("http://195.251.30.202:8080/dspace-oai/request");
		urls.add("http://lara.inist.fr/dspace-oai/request");
		urls.add("http://dspace.bsu.edu.ru/oai/request");
		urls.add("http://repository.urosario.edu.co/dspace-oai/request");
		urls.add("http://eciencia.urjc.es/dspace-oai/request");
		urls.add("http://sci-gems.math.bas.bg:8080/oai/request");
		urls.add("https://research-repository.st-andrews.ac.uk/dspace-oai/request");
		urls.add("http://www2.cita-aragon.es/oai/request");
		urls.add("http://drc.library.utoledo.edu/oai/request");
		urls.add("http://elartu.tntu.edu.ua/oai/request");
		urls.add("http://ru.ffyl.unam.mx:8080/oai/request");
		urls.add("http://doc.sspal.it/dspace-oai/request");
		urls.add("http://comum.rcaap.pt/oaiextended/request");
		urls.add("http://repository.edulll.gr/edulll-oai/request");
		urls.add("http://repositorioaberto.univ-ab.pt/oai/request");
		urls.add("http://rihuc.huc.min-saude.pt/oai/request");
		urls.add("http://dspace-unipr.cilea.it/dspace-oai/request");
		urls.add("http://repositorio.ispa.pt/oaiextended/request");
		urls.add("http://repositorio.ipcb.pt/oaiextended/request");
		urls.add("http://roar.uel.ac.uk/oai/request");
		urls.add("http://dspace.uel.ac.uk/oai/request");
		urls.add("http://dcommon.bu.edu:8080/oai/request");
		urls.add("http://repositorio.usfq.edu.ec/oai/request");
		urls.add("http://repository.abertay.ac.uk:8080/oai/request");
		return urls;
	}
	
}


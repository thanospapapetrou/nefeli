//package com.github.thanospapapetrou.nefeli.oai.pmh.impl.server;
//
//import java.net.URI;
//import java.util.Date;
//
//import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmh;
//import com.github.thanospapapetrou.nefeli.oai.pmh.api.OaiPmhException;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Identify;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListIdentifiers;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListMetadataFormats;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListRecords;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.ListSets;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.MetadataPrefix;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Record;
//import com.github.thanospapapetrou.nefeli.oai.pmh.domain.SetSpec;
//
//public class AbstractOaiPmhServer implements OaiPmh {
//	@Override
//	public Identify identify() throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ListMetadataFormats listMetadataFormats(URI identifier) throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ListSets listSets() throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ListSets listSets(String resumptionToken) throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ListIdentifiers listIdentifiers(MetadataPrefix metadataPrefix, Date from, Date until, SetSpec set) throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ListIdentifiers listIdentifiers(String resumptionToken) throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ListRecords listRecords(MetadataPrefix metadataPrefix, Date from, Date until, SetSpec set) throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ListRecords listRecords(String resumptionToken) throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Record getRecord(URI identifier, MetadataPrefix metadataPrefix) throws OaiPmhException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void request(@QueryParam("verb") final String verb) {
//		
//	}
//}

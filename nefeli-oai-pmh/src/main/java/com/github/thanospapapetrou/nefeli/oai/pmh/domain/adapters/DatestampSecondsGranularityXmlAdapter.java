package com.github.thanospapapetrou.nefeli.oai.pmh.domain.adapters;

import com.github.thanospapapetrou.nefeli.oai.pmh.domain.Granularity;

/**
 * XML adapter that marshals/unmarshals datestamps to/from strings using seconds granularity.
 * 
 * @author thanos
 */
public class DatestampSecondsGranularityXmlAdapter extends DatestampGranularityXmlAdapter {
	/**
	 * Construct a new datestamp seconds granularity XML adapter.
	 */
	public DatestampSecondsGranularityXmlAdapter() {
		super(Granularity.SECONDS);
	}
}


package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Zust;

@Component
@Transactional
public class ZustToStringConverter implements Converter<Zust, String> {

	@Override
	public String convert(final Zust zust) {
		String result;

		if (zust == null)
			result = null;
		else
			result = String.valueOf(zust.getId());

		return result;
	}
}

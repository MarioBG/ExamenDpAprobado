
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SubscriptionNewspaper;

@Component
@Transactional
public class SubscriptionNewspaperToStringConverter implements Converter<SubscriptionNewspaper, String> {

	@Override
	public String convert(final SubscriptionNewspaper subscriptionNewspaper) {
		String result;

		if (subscriptionNewspaper == null)
			result = null;
		else
			result = String.valueOf(subscriptionNewspaper.getId());

		return result;
	}
}

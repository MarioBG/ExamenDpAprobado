
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SubscriptionNewspaperRepository;
import domain.SubscriptionNewspaper;

@Component
@Transactional
public class StringToSubscriptionNewspaperConverter implements Converter<String, SubscriptionNewspaper> {

	@Autowired
	SubscriptionNewspaperRepository	subscriptionNewspaperRepository;


	@Override
	public SubscriptionNewspaper convert(final String text) {
		SubscriptionNewspaper result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.subscriptionNewspaperRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

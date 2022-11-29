package edu.vtac.roveBaseProject;

import java.util.function.Consumer;

import org.mockito.internal.matchers.CapturingMatcher;

class CSCapturingMatcher<T> extends CapturingMatcher<T> {

	private Consumer<T> consumer;

	@Override
	public void captureFrom(Object argument) {
		super.captureFrom(argument);
		if (consumer != null) {
			consumer.accept((T) argument);
		}
	}

	public void setConsumer(Consumer<T> consumer) {
		this.consumer = consumer;
	}
}
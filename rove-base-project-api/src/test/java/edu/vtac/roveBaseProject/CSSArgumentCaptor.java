package edu.vtac.roveBaseProject;

import java.util.List;
import java.util.function.Consumer;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.progress.HandyReturnValues;

public class CSSArgumentCaptor<T> extends ArgumentCaptor<T> {

	HandyReturnValues handyReturnValues = new HandyReturnValues();

	private final CSCapturingMatcher<T> capturingMatcher = new CSCapturingMatcher<T>();
	private final Class<T> clazz;

	CSSArgumentCaptor(Class<T> clazz, Consumer<T> consumer) {
		this.clazz = clazz;
		capturingMatcher.setConsumer(consumer);
	}

	public T capture() {
		Mockito.argThat(capturingMatcher);
		return handyReturnValues.returnFor(clazz);
	}

	public T getValue() {
		return this.capturingMatcher.getLastValue();
	}

	public List<T> getAllValues() {
		return this.capturingMatcher.getAllValues();
	}

	public static <T> CSSArgumentCaptor<T> forClass(Class<T> clazz, Consumer<T> consumer) {

		return new CSSArgumentCaptor<T>(clazz, consumer);
	}

}

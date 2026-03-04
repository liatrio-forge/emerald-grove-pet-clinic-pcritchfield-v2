package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageSwitchAdviceTests {

	final LanguageSwitchAdvice advice = new LanguageSwitchAdvice();

	@Test
	void shouldBuildLanguageUrlsFromSimplePath() {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/vets.html");
		Model model = new ExtendedModelMap();

		advice.addLanguageUrls(request, model);

		assertThat(model.getAttribute("langUrlEn")).isEqualTo("/vets.html?lang=en");
		assertThat(model.getAttribute("langUrlEs")).isEqualTo("/vets.html?lang=es");
		assertThat(model.getAttribute("langUrlDe")).isEqualTo("/vets.html?lang=de");
	}

	@Test
	void shouldPreserveExistingQueryParams() {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/owners");
		request.setQueryString("lastName=Davis&page=2");

		Model model = new ExtendedModelMap();
		advice.addLanguageUrls(request, model);

		assertThat(model.getAttribute("langUrlEs")).isEqualTo("/owners?lastName=Davis&page=2&lang=es");
	}

	@Test
	void shouldReplaceExistingLangParam() {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/owners");
		request.setQueryString("lastName=Davis&lang=en");

		Model model = new ExtendedModelMap();
		advice.addLanguageUrls(request, model);

		assertThat(model.getAttribute("langUrlEs")).isEqualTo("/owners?lastName=Davis&lang=es");
		assertThat((String) model.getAttribute("langUrlEs")).doesNotContain("lang=en");
	}

}

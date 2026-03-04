package org.springframework.samples.petclinic.system;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriComponentsBuilder;

@ControllerAdvice
class LanguageSwitchAdvice {

	@ModelAttribute
	void addLanguageUrls(HttpServletRequest request, Model model) {
		UriComponentsBuilder base = UriComponentsBuilder.fromPath(request.getRequestURI())
			.query(request.getQueryString());

		model.addAttribute("langUrlEn", base.cloneBuilder().replaceQueryParam("lang", "en").toUriString());
		model.addAttribute("langUrlEs", base.cloneBuilder().replaceQueryParam("lang", "es").toUriString());
		model.addAttribute("langUrlDe", base.cloneBuilder().replaceQueryParam("lang", "de").toUriString());
	}

}

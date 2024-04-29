import { Component, HostBinding, Input, TemplateRef } from '@angular/core';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
	// adds padding to the top of the document, so the content is below the header
	@HostBinding('class.cds--header') headerClass = true;
	secondAction = false;
	firstAction = false;
	position: number | undefined;
	@Input() open = false;
	@Input() customTrigger: TemplateRef<any> | undefined;
	@Input() placement: 'bottom' | 'top' = 'bottom';
}

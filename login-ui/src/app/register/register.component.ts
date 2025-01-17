import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../_services/auth.service';
import { CommonModule, NgClass } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, 
	CommonModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
	form: any = {
		username: null,
		email: null,
		password: null
	};
	isSuccessful = false;
	isSignUpFailed = false;
	errorMessage = '';
	
	constructor(private authService: AuthService) {
		
	}
	
	onSubmit(): void {
		const {username, email, password} = this.form;
		this.authService.register(username, email, password).subscribe({
			next: data => {
				console.log(data);
				this.isSuccessful = true;
				this.isSignUpFailed = false;
			},
			error: err => {
				this.errorMessage = err.error.message;
				this.isSignUpFailed = true;
			}
		});
	}
}

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { StorageService } from '../_services/storage.service';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgClass, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
	
	form: any = {
		username: null,
		password: null
	};
	isLoggedIn = false;
	isLoginFailed = false;
	errorMessage = '';
	roles: string[] = []
	
	constructor(private authService: AuthService, private storageService: StorageService) {}
	
    ngOnInit(): void {
        if (this.storageService.isLoggedIn()) {
			this.isLoggedIn = true;
			this.roles = this.storageService.getUser().roles;
		}
    }
	
	onSubmit(): void {
		const {username, password} = this.form;
		// Call
		this.authService.login(username, password).subscribe({
			next: data => {
				this.storageService.saveUser(data);
				this.isLoginFailed = false
				this.isLoggedIn = true
				this.roles = this.storageService.getUser().roles;
				this.reloadPage();
			}, 
			error: err => {
				this.errorMessage = err.error.message;
				this.isLoginFailed = true;
			}
		});
	}
    reloadPage(): void {
        window.location.reload();
    }
	
	

}

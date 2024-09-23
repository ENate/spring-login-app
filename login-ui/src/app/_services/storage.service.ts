import { Injectable } from '@angular/core';

/** 
 * manages user information (username, email, roles) 
 * inside Browser’s Session Storage. 
 * For Logout, we will clear this Session Storage.
 */
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }
  // Clean the storage
  clean(): void {
	window.sessionStorage.clear();
  }
  // Save User in session
  public saveUser(user: any): void {
	window.sessionStorage.removeItem(USER_KEY);
	window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }
  // Get user
  public getUser(): any {
	const user = window.sessionStorage.getItem(USER_KEY);
	if (user) {
		return JSON.parse(user);
	}
	return {}
  }
  
  // Check if user is logged in
  public isLoggedIn(): boolean {
	const user = window.sessionStorage.getItem(USER_KEY);
	if (user) {
		return true;
	}
	return false;
  }
}

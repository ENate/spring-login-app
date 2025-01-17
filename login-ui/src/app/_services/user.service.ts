import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/**
 * This service provides methods to access public and protected resources.
 * Because HttpOnly Cookies will be automatically sent along with HTTP
 * requests (via Http Interceptor), so we just simply use Http module without caring about JWT.
 */
const API_URL = 'http://localhost:8080/api/test/';

@Injectable({
    providedIn: 'root',
})
export class UserService {
    constructor(private http: HttpClient) {}

    // For home and all endpoint
    getPublicContent(): Observable<any> {
        return this.http.get(API_URL + 'all', { responseType: 'text' });
    }

    // For home and all endpoint
    getUserBoard(): Observable<any> {
        return this.http.get(API_URL + 'user', { responseType: 'text' });
    }
    // For home and all endpoint
    getModeratorBoard(): Observable<any> {
        return this.http.get(API_URL + 'mod', { responseType: 'text' });
    }

    // For home and all endpoint
    getAdmindBoard(): Observable<any> {
        return this.http.get(API_URL + 'admin', { responseType: 'text' });
    }
}

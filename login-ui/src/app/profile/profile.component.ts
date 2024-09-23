import { Component, OnInit } from '@angular/core';
import { StorageService } from '../_services/storage.service';

/**
 * This Component gets current User from Storage using StorageService 
 * and show information (username, token, email, roles).
 */
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
	
	currentUser: any;
	
	constructor(private storageService: StorageService) {}
	
	
    ngOnInit(): void {
        this.currentUser = this.storageService.getUser();
    }

}

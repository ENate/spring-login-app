import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-user',
  standalone: true,
  imports: [],
  templateUrl: './board-user.component.html',
  styleUrl: './board-user.component.css'
})
export class BoardUserComponent implements OnInit {
	
	content?: string;
	
	constructor(private userService: UserService) {}
	
	ngOnInit(): void {
		this.userService.getUserBoard().subscribe({
			next: data => {
				this.content = data;
			}, 
			error: err => {
				console.log(err)
				if (err.error) {
					this.content = JSON.parse(err.error).message;
				} else {
					this.content = "Error with status: "+ err.status
				}
			}
		});
	}

}

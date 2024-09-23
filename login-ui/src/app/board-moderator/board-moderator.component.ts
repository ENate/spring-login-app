import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-moderator',
  standalone: true,
  imports: [],
  templateUrl: './board-moderator.component.html',
  styleUrl: './board-moderator.component.css'
})
export class BoardModeratorComponent implements OnInit {
	content?: string; 
	
	constructor(private userService: UserService) {}
	
	ngOnInit(): void {
		this.userService.getModeratorBoard().subscribe({
			next: data => {
				this.content = data;
			},
			error: err => {
				console.log(err) // check semi column;
				if (err.error) {
					this.content = JSON.parse(err.error).message;
				} else {
					this.content = "Error with status: " + err.status;
				}
			}
		});
	}
}

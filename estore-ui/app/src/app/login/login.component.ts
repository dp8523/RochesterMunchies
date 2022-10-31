import { Component, OnInit } from '@angular/core'; 
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: String | undefined;

  constructor(private userService: UserService) { }

  ngOnInit( ): void {
    
  }

  login(username: String): void {
    username.trim();
    this.userService.login(username).subscribe();
  }

  register(username: String): void {
    
    username.trim();
    this.userService.register(username).subscribe();
  }

  
}

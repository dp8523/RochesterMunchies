import { Component, OnInit } from '@angular/core'; 
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  htmlPage: String | undefined;
  user: User | undefined;

  constructor(private userService: UserService) { }

  ngOnInit( ): void {
    
  }
  //hello

  login(username: String): void {

    console.log("Before");
    username = username.trim();
    this.userService.login(username).subscribe(user => this.user = user);

    console.log(this.user);
    if (this.user){

      console.log("User exists!");
      if(this.user.username === "admin"){
      this.htmlPage = "/inventory";
      }
      else if (this.user.username){
        this.htmlPage = "/buyer_inventory";
      }
    }
    this.htmlPage = "/login";
  }

  register(username: String): void {
    username.trim();
    this.userService.register(username).subscribe(user => this.user = user);

    if (this.user){
      console.log("User exists!");
      if (this.user.username){
        this.htmlPage = "/buyer_inventory";
      }
    }
    this.htmlPage = "/login";
  }

  
}

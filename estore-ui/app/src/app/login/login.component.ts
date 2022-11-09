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

  //user1: User = { username: "null" } as unknown as User;

  constructor(private userService: UserService) { }

  ngOnInit( ): void {
    
  }

  login(username: string): void {

    console.log("Before");
    //console.log(this.user1);
    username = username.trim();
    console.log(username.trim());

    if (username){
      console.log("Hello");
      this.userService.login(username).subscribe(user => {
        console.log("After");
        console.log(user);
        if (user != null){
          this.user = user;
          console.log("User exists!");
          if(username === "admin"){
            console.log("admin user");
            this.htmlPage = "/inventory";
          }
          else{
            this.htmlPage = "/buyer_inventory";
          }
        }
        } );
      
    }
    /*
    if(username === "admin"){
      this.htmlPage = "/inventory";
    }
    else if (username){
      this.htmlPage = "/buyer_inventory";
    }
    else{
      this.htmlPage = "/login";
    } 
    */ 
  }

  register(username: string): void {
    username.trim();
    console.log("Before");
    this.userService.register(username).subscribe(user => this.user = user);

    console.log(this.user);
    if (this.user){
      console.log("User exists!");
      if (username){
        this.htmlPage = "/buyer_inventory";
      }
    }
    this.htmlPage = "/login";
  }
}

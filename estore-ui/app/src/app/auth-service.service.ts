import { Injectable } from '@angular/core';
import { User } from './user';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(private userService: UserService) { }

  user: User | undefined;
  isLoggedin: Boolean  = false;


  login(username: string): void {
    
    this.userService.login(username).subscribe( user => {
      this.user = user;
      if(this.user){
        sessionStorage.setItem("user", JSON.stringify(this.user));
        this.isLoggedin = true;
        sessionStorage.setItem("login", "true");
      }
      else{
        sessionStorage.setItem("user", "");
      }
    } )

  }

  logout(): void{
    this.user = undefined;
    sessionStorage.setItem("user", "");
    this.isLoggedin = false;
    sessionStorage.setItem("login", "false");
  }

  register(username: string): void {
    this.userService.register(username).subscribe(user => { 
      this.user = user;
      if (this.user){
        sessionStorage.setItem("user", JSON.stringify(this.user));
        this.isLoggedin = true;
        sessionStorage.setItem("login", "true");
      }
    })
    
  }

}

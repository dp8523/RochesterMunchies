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
      this.isLoggedin = true;
    } )

  }

  logout(): void{
    this.user = undefined;
    this.isLoggedin = false;
  }

  register(username: string): void {
    this.userService.register(username).subscribe(user => { 
      this.user = user;
      this.isLoggedin = true;
    })
    
  }

}

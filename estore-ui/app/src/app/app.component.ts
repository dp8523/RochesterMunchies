import { Component } from '@angular/core';
import { User } from './user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'Rochester Munchies';
  user: User | undefined;
  username: string | undefined;
  login: Boolean = false;

  loggedin(): Boolean{
    
    if(sessionStorage['login'] == "false" || sessionStorage['login'] == null ){
      return false;
    }
    return true;
  }

  isAdmin(): Boolean {

    if(sessionStorage['user'] == "" || sessionStorage['user'] == null ){
      this.user = undefined;
      return false;
    }
    else{
      this.user = JSON.parse(sessionStorage['user']);
      this.username = this.user?.username;

      return this.username == "admin";
    }
  }
}

import { Component, OnInit } from '@angular/core'; 
import { User } from '../user';
import { Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User | undefined;
  isLoggedin: Boolean = true;

  constructor( 
    private router: Router,
    private authService: AuthServiceService) { }

  ngOnInit( ): void {
    
  }

  async login(username: string): Promise<void> {

    username = username.trim();
    if (username){
      this.authService.login(username)
      await new Promise(f => setTimeout(f, 500));
      this.user = this.authService.user;

      if (this.user != null){
        if(sessionStorage['login'] == "true"){
          this.isLoggedin = true;
        }
        if (this.user?.username === "admin"){
          this.router.navigateByUrl('/inventory');
        }
        else{
          this.router.navigateByUrl('/buyer_inventory');
        }
      }
      else{
        alert("Invalid Login")
      }
    }
  }

  async register(username: string): Promise<void> {
    
    username = username.trim();
    if (username){
      this.authService.register(username)
      await new Promise(f => setTimeout(f, 500));
      this.user = this.authService.user;

      if (this.user != null){
        if(sessionStorage['login'] == "true"){
          this.isLoggedin = true;
        }
        this.router.navigateByUrl('/buyer_inventory'); 
      }
    }
  }

  logout(): void {
    this.authService.logout();

    if (sessionStorage['user'] == ""){
      this.user = undefined;
    }
    if(sessionStorage['login'] == "false"){
      this.isLoggedin = !this.isLoggedin;
    }
  }

  Loggedin(): Boolean{

    if (sessionStorage['login'] == "true"){
      return true;
    }
    else{
      return false;
    }
  }
}
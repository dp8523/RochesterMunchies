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
        if (this.user?.username === "admin"){
          this.router.navigateByUrl('/inventory');
        }
        else{
          this.router.navigateByUrl('/buyer_inventory');
        }
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
        this.router.navigateByUrl('/buyer_inventory'); 
      }
    }
  }
}
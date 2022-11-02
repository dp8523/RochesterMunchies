import { HttpClient,  HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs';

@Injectable({ providedIn: 'root'})
export class UserService {

  private userURL = 'http://localhost:8080/buyers';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json;'})
  };

  constructor(private http: HttpClient) { }

  login(username: String): Observable<User> {
    const url = `${this.userURL}/${username}`;
    return this.http.get<User>(url)
    .pipe(catchError(this.handleError<User>(`getUser username=${username}`)))
  }

  register(username: String): Observable<User> {
    return this.http.post<User>(this.userURL, username, this.httpOptions)
    .pipe(catchError(this.handleError<User>(`createUser username=${username}`)))
  }

  // Handle Http operation that failed
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}

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

  login(username: string): Observable<User> {
    const url = `${this.userURL}/${username}`;
    return this.http.get<User>(url, this.httpOptions)
    .pipe(catchError(this.handleError<User>(`getUser username=${username}`)))
  }

  register(username: string): Observable<User> {
    const url = `${this.userURL}/${username}`;
    return this.http.post<User>(url, this.httpOptions)
    .pipe(catchError(this.handleError<User>(`createUser username=${username}`)))
  }

  addCart(username: string, snackId: number): Observable<User> {
    const url = `${this.userURL}/a/${username}/${snackId}`;
    return this.http.put<User>(url, this.httpOptions)
    .pipe(catchError(this.handleError<User>(`addCart username=${username} , snackId=${snackId}`)))
  }

  deleteCart(username: string, snackId: number): Observable<User> {
    const url = `${this.userURL}/d/${username}/${snackId}`;
    return this.http.delete<User>(url, this.httpOptions)
    .pipe(catchError(this.handleError<User>(`deleteCart username=${username} , snackId=${snackId}`)))
  }

  getTotalCost(username: string): Observable<number>{
    const url = `${this.userURL}/${username}/cartTotal`;
    return this.http.get<number>(url,this.httpOptions)
    .pipe(catchError(this.handleError<number>(`getTotalCost username=${username}`)))
    
  }

  // Handle Http operation that failed
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}

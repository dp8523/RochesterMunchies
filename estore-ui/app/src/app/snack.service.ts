import { Injectable } from '@angular/core';
import { Snack } from './Snack';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap} from 'rxjs/operators';

@Injectable({ providedIn: 'root'})
export class SnackService {

  private snacksURL = 'http://localhost:8080/snacks'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'applications/json' })
  };

  constructor( private http: HttpClient) { }

  // GET snacks from the server
  getSnacks(): Observable<Snack[]>{
    return this.http.get<Snack[]>(this.snacksURL)
    .pipe(catchError(this.handleError<Snack[]>('getSnacks', [])));
  }

  // GET snack by id. Will 404 if id not found
  getSnack(id: number): Observable<Snack> {
    const url = `${this.snacksURL}/${id}`;
    return this.http.get<Snack>(url)
    .pipe(catchError(this.handleError<Snack>(`getSnack id=${id}`)));
  }

  //GET snacks whose name contains search item
  searchSnacks(term: string): Observable<Snack[]> {
    if(!term.trim()) {
      return of([]);
    }
    return this.http.get<Snack[]>(`${this.snacksURL}/?name=${term}`).pipe(
      catchError(this.handleError<Snack[]>('searchSnacks', []))
    );
  }

  ///////////// Save Methods /////////////////

  // POST: add a new snack to the server
  addSnack(snack: Snack): Observable<Snack> {
    return this.http.post<Snack>(this.snacksURL, snack, this.httpOptions)
    .pipe(catchError(this.handleError<Snack>('addSnack')));
  }

  // DELETE: Delete the snack from the server
  deleteSnack(id: number): Observable<Snack> {
    const url = `${this.snacksURL}/${id}`;
    return this.http.delete<Snack>(url, this.httpOptions)
    .pipe(catchError(this.handleError<Snack>('deleteSnack')));
  }

  /** PUT: update the snack on the server */
  updateSnack(snack:Snack): Observable<any> {
    return this.http.put(this.snacksURL, snack, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateSnack'))
    );
  }

  // Handle Http operation that failed
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }

}

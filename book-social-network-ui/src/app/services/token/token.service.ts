import { Injectable } from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token : string)
  {
    localStorage.setItem('token', token);
  }

  get token()
  {
    return localStorage.getItem('token') as string;
  }

  isTokenNotValid()
  {
    return !this.isTokenValid();
  }

  private isTokenValid()
  {
    const token = this.token;

    if (!token)
    {
      return false;
    }

    // le token existe, on va le décoder (installer d'abord @auth0/angular-jwt : npm install @auth0/angular-jwt)
    const jwtHelper : JwtHelperService = new JwtHelperService();  //JwtHelperService vient du @auth0/angular-jwt

    // vérifie si la date du token est expirée
    const isTokenExpired : boolean = jwtHelper.isTokenExpired(token);
    if (isTokenExpired)
    {
      localStorage.clear(); //on éfface ce qu'il y a dans le local storage car expiré
      return false;
    }

    return true;
  }
}

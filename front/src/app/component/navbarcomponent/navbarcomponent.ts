import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Authservice } from '../../services/authservice';

@Component({
  selector: 'app-navbarcomponent',
  standalone: false,
  styleUrl: './navbarcomponent.css',
  templateUrl: './navbarcomponent.html',
})
export class Navbarcomponent {
  constructor(public auth: Authservice, private router: Router) {}

  salir() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}

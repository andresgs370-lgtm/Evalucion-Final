import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Logincomponent } from './component/logincomponent/logincomponent';
import { Admincomponent } from './component/admincomponent/admincomponent';
import { Usercomponent } from './component/usercomponent/usercomponent';
import { authguard } from './services/authguard';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Logincomponent },
  { path: 'admin', component: Admincomponent, canActivate: [authguard], data: { role: 'ADMIN' } },
  { path: 'tienda', component: Usercomponent, canActivate: [authguard], data: { role: 'USER' } },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

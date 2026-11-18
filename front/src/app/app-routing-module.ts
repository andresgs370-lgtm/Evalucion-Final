import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Paginaprincipalcomponent } from './component/paginaprincipalcomponent/paginaprincipalcomponent';
import {Logincomponent} from './component/logincomponent/logincomponent';
import {Registrocomponent} from './component/registrocomponent/registrocomponent';
import {Tiendacomponent} from './component/tiendacomponent/tiendacomponent';

const routes: Routes = [
  { path: '', component: Paginaprincipalcomponent },
  {path: 'login', component: Logincomponent },
  {path: 'registro', component: Registrocomponent },
  {path: 'tiendacomponent', component: Tiendacomponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

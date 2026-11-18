import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Paginaprincipalcomponent } from './component/paginaprincipalcomponent/paginaprincipalcomponent';
import { Pokemoncomponent } from './component/pokemoncomponent/pokemoncomponent';
import {Logincomponent} from './component/logincomponent/logincomponent';
import {Registrocomponent} from './component/registrocomponent/registrocomponent';
import {Paiscomponent}  from './component/paiscomponent/paiscomponent';

const routes: Routes = [
  { path: '', component: Paginaprincipalcomponent },
  { path: 'pokemon', component: Pokemoncomponent },
  {path: 'login', component: Logincomponent },
  {path: 'registro', component: Registrocomponent },
  {path: 'pais', component: Paiscomponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Paginaprincipalcomponent } from './component/paginaprincipalcomponent/paginaprincipalcomponent';
import { Pokemoncomponent } from './component/pokemoncomponent/pokemoncomponent';

const routes: Routes = [
  { path: '', component: Paginaprincipalcomponent },
  { path: 'pokemon', component: Pokemoncomponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

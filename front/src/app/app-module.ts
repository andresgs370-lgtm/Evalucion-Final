import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Navbarcomponent } from './component/navbarcomponent/navbarcomponent';
import { Paginaprincipalcomponent } from './component/paginaprincipalcomponent/paginaprincipalcomponent';
import { Footercomponent } from './component/footercomponent/footercomponent';
import { Pokemoncomponent } from './component/pokemoncomponent/pokemoncomponent';
import { Registrocomponent } from './component/registrocomponent/registrocomponent';
import { Logincomponent } from './component/logincomponent/logincomponent';
import { Paiscomponent } from './component/paiscomponent/paiscomponent';

@NgModule({
  declarations: [
    App,
    Navbarcomponent,
    Paginaprincipalcomponent,
    Footercomponent,
    Pokemoncomponent,
    Registrocomponent,
    Logincomponent,
    Paiscomponent,
  ],
  imports: [BrowserModule, AppRoutingModule],
  providers: [provideBrowserGlobalErrorListeners()],
  bootstrap: [App],
})
export class AppModule {}

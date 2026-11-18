import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing-module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { App } from './app';
import { Navbarcomponent } from './component/navbarcomponent/navbarcomponent';
import { Paginaprincipalcomponent } from './component/paginaprincipalcomponent/paginaprincipalcomponent';
import { Footercomponent } from './component/footercomponent/footercomponent';
import { Registrocomponent } from './component/registrocomponent/registrocomponent';
import { Logincomponent } from './component/logincomponent/logincomponent';
import { Registroservice } from './services/registroservice';
import { Tiendacomponent } from './component/tiendacomponent/tiendacomponent';

@NgModule({
  declarations: [
    App,
    Navbarcomponent,
    Paginaprincipalcomponent,
    Footercomponent,
    Registrocomponent,
    Logincomponent,
    Tiendacomponent,
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [provideBrowserGlobalErrorListeners(), Registroservice],
  bootstrap: [App],
})
export class AppModule {}

import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Navbarcomponent } from './component/navbarcomponent/navbarcomponent';
import { Footercomponent } from './component/footercomponent/footercomponent';
import { Logincomponent } from './component/logincomponent/logincomponent';
import { Admincomponent } from './component/admincomponent/admincomponent';
import { Usercomponent } from './component/usercomponent/usercomponent';
import { tokeninterceptor } from './services/tokeninterceptor';

@NgModule({
  declarations: [App, Navbarcomponent, Footercomponent, Logincomponent, Admincomponent, Usercomponent],
  imports: [BrowserModule, AppRoutingModule, FormsModule],
  providers: [provideBrowserGlobalErrorListeners(), provideHttpClient(withInterceptors([tokeninterceptor]))],
  bootstrap: [App],
})
export class AppModule {}

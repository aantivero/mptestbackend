import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { DebinappSharedModule, UserRouteAccessService } from './shared';
import { DebinappAppRoutingModule} from './app-routing.module';
import { DebinappHomeModule } from './home/home.module';
import { DebinappAdminModule } from './admin/admin.module';
import { DebinappAccountModule } from './account/account.module';
import { DebinappEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        DebinappAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        DebinappSharedModule,
        DebinappHomeModule,
        DebinappAdminModule,
        DebinappAccountModule,
        DebinappEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class DebinappAppModule {}

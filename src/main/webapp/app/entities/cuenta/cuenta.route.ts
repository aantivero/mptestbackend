import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CuentaComponent } from './cuenta.component';
import { CuentaDetailComponent } from './cuenta-detail.component';
import { CuentaPopupComponent } from './cuenta-dialog.component';
import { CuentaDeletePopupComponent } from './cuenta-delete-dialog.component';

export const cuentaRoute: Routes = [
    {
        path: 'cuenta',
        component: CuentaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.cuenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cuenta/:id',
        component: CuentaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.cuenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cuentaPopupRoute: Routes = [
    {
        path: 'cuenta-new',
        component: CuentaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.cuenta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cuenta/:id/edit',
        component: CuentaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.cuenta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cuenta/:id/delete',
        component: CuentaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'debinappApp.cuenta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactMechPposType, ContactMechPposType } from 'app/shared/model/contact-mech-ppos-type.model';
import { ContactMechPposTypeService } from './contact-mech-ppos-type.service';
import { ContactMechPposTypeComponent } from './contact-mech-ppos-type.component';
import { ContactMechPposTypeDetailComponent } from './contact-mech-ppos-type-detail.component';
import { ContactMechPposTypeUpdateComponent } from './contact-mech-ppos-type-update.component';

@Injectable({ providedIn: 'root' })
export class ContactMechPposTypeResolve implements Resolve<IContactMechPposType> {
  constructor(private service: ContactMechPposTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactMechPposType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactMechPposType: HttpResponse<ContactMechPposType>) => {
          if (contactMechPposType.body) {
            return of(contactMechPposType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactMechPposType());
  }
}

export const contactMechPposTypeRoute: Routes = [
  {
    path: '',
    component: ContactMechPposTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactMechPposTypeDetailComponent,
    resolve: {
      contactMechPposType: ContactMechPposTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactMechPposTypeUpdateComponent,
    resolve: {
      contactMechPposType: ContactMechPposTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactMechPposTypeUpdateComponent,
    resolve: {
      contactMechPposType: ContactMechPposTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

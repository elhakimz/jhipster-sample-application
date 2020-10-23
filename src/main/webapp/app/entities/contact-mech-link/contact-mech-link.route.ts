import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactMechLink, ContactMechLink } from 'app/shared/model/contact-mech-link.model';
import { ContactMechLinkService } from './contact-mech-link.service';
import { ContactMechLinkComponent } from './contact-mech-link.component';
import { ContactMechLinkDetailComponent } from './contact-mech-link-detail.component';
import { ContactMechLinkUpdateComponent } from './contact-mech-link-update.component';

@Injectable({ providedIn: 'root' })
export class ContactMechLinkResolve implements Resolve<IContactMechLink> {
  constructor(private service: ContactMechLinkService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactMechLink> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactMechLink: HttpResponse<ContactMechLink>) => {
          if (contactMechLink.body) {
            return of(contactMechLink.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactMechLink());
  }
}

export const contactMechLinkRoute: Routes = [
  {
    path: '',
    component: ContactMechLinkComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechLink.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactMechLinkDetailComponent,
    resolve: {
      contactMechLink: ContactMechLinkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechLink.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactMechLinkUpdateComponent,
    resolve: {
      contactMechLink: ContactMechLinkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechLink.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactMechLinkUpdateComponent,
    resolve: {
      contactMechLink: ContactMechLinkResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.contactMechLink.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

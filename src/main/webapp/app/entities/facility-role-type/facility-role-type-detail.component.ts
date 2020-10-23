import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacilityRoleType } from 'app/shared/model/facility-role-type.model';

@Component({
  selector: 'jhi-facility-role-type-detail',
  templateUrl: './facility-role-type-detail.component.html',
})
export class FacilityRoleTypeDetailComponent implements OnInit {
  facilityRoleType: IFacilityRoleType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityRoleType }) => (this.facilityRoleType = facilityRoleType));
  }

  previousState(): void {
    window.history.back();
  }
}

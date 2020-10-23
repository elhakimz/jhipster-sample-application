import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacilityRole } from 'app/shared/model/facility-role.model';

@Component({
  selector: 'jhi-facility-role-detail',
  templateUrl: './facility-role-detail.component.html',
})
export class FacilityRoleDetailComponent implements OnInit {
  facilityRole: IFacilityRole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityRole }) => (this.facilityRole = facilityRole));
  }

  previousState(): void {
    window.history.back();
  }
}

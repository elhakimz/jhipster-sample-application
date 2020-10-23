import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacility, Facility } from 'app/shared/model/facility.model';
import { FacilityService } from './facility.service';

@Component({
  selector: 'jhi-facility-update',
  templateUrl: './facility-update.component.html',
})
export class FacilityUpdateComponent implements OnInit {
  isSaving = false;
  facilities: IFacility[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
    area: [],
    facilityType: [],
    parent: [],
  });

  constructor(protected facilityService: FacilityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facility }) => {
      this.updateForm(facility);

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));
    });
  }

  updateForm(facility: IFacility): void {
    this.editForm.patchValue({
      id: facility.id,
      code: facility.code,
      description: facility.description,
      area: facility.area,
      facilityType: facility.facilityType,
      parent: facility.parent,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facility = this.createFromForm();
    if (facility.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityService.update(facility));
    } else {
      this.subscribeToSaveResponse(this.facilityService.create(facility));
    }
  }

  private createFromForm(): IFacility {
    return {
      ...new Facility(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
      area: this.editForm.get(['area'])!.value,
      facilityType: this.editForm.get(['facilityType'])!.value,
      parent: this.editForm.get(['parent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacility>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IFacility): any {
    return item.id;
  }
}

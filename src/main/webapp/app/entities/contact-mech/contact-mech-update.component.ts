import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContactMech, ContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from './contact-mech.service';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from 'app/entities/contact-mech-type/contact-mech-type.service';

@Component({
  selector: 'jhi-contact-mech-update',
  templateUrl: './contact-mech-update.component.html',
})
export class ContactMechUpdateComponent implements OnInit {
  isSaving = false;
  contactmechtypes: IContactMechType[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    areaCode: [],
    countryCode: [],
    contactNumber: [],
    address1: [],
    address2: [],
    directions: [],
    electronicAddress: [],
    contactMechanismType: [],
  });

  constructor(
    protected contactMechService: ContactMechService,
    protected contactMechTypeService: ContactMechTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMech }) => {
      this.updateForm(contactMech);

      this.contactMechTypeService.query().subscribe((res: HttpResponse<IContactMechType[]>) => (this.contactmechtypes = res.body || []));
    });
  }

  updateForm(contactMech: IContactMech): void {
    this.editForm.patchValue({
      id: contactMech.id,
      code: contactMech.code,
      areaCode: contactMech.areaCode,
      countryCode: contactMech.countryCode,
      contactNumber: contactMech.contactNumber,
      address1: contactMech.address1,
      address2: contactMech.address2,
      directions: contactMech.directions,
      electronicAddress: contactMech.electronicAddress,
      contactMechanismType: contactMech.contactMechanismType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactMech = this.createFromForm();
    if (contactMech.id !== undefined) {
      this.subscribeToSaveResponse(this.contactMechService.update(contactMech));
    } else {
      this.subscribeToSaveResponse(this.contactMechService.create(contactMech));
    }
  }

  private createFromForm(): IContactMech {
    return {
      ...new ContactMech(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      areaCode: this.editForm.get(['areaCode'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      contactNumber: this.editForm.get(['contactNumber'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      directions: this.editForm.get(['directions'])!.value,
      electronicAddress: this.editForm.get(['electronicAddress'])!.value,
      contactMechanismType: this.editForm.get(['contactMechanismType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactMech>>): void {
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

  trackById(index: number, item: IContactMechType): any {
    return item.id;
  }
}

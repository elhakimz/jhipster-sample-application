import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContactMechType, ContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from './contact-mech-type.service';

@Component({
  selector: 'jhi-contact-mech-type-update',
  templateUrl: './contact-mech-type-update.component.html',
})
export class ContactMechTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
  });

  constructor(
    protected contactMechTypeService: ContactMechTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechType }) => {
      this.updateForm(contactMechType);
    });
  }

  updateForm(contactMechType: IContactMechType): void {
    this.editForm.patchValue({
      id: contactMechType.id,
      code: contactMechType.code,
      name: contactMechType.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactMechType = this.createFromForm();
    if (contactMechType.id !== undefined) {
      this.subscribeToSaveResponse(this.contactMechTypeService.update(contactMechType));
    } else {
      this.subscribeToSaveResponse(this.contactMechTypeService.create(contactMechType));
    }
  }

  private createFromForm(): IContactMechType {
    return {
      ...new ContactMechType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactMechType>>): void {
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
}

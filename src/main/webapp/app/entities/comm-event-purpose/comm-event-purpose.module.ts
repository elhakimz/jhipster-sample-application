import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CommEventPurposeComponent } from './comm-event-purpose.component';
import { CommEventPurposeDetailComponent } from './comm-event-purpose-detail.component';
import { CommEventPurposeUpdateComponent } from './comm-event-purpose-update.component';
import { CommEventPurposeDeleteDialogComponent } from './comm-event-purpose-delete-dialog.component';
import { commEventPurposeRoute } from './comm-event-purpose.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(commEventPurposeRoute)],
  declarations: [
    CommEventPurposeComponent,
    CommEventPurposeDetailComponent,
    CommEventPurposeUpdateComponent,
    CommEventPurposeDeleteDialogComponent,
  ],
  entryComponents: [CommEventPurposeDeleteDialogComponent],
})
export class JhipsterSampleApplicationCommEventPurposeModule {}

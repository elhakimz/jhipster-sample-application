import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CommEventComponent } from './comm-event.component';
import { CommEventDetailComponent } from './comm-event-detail.component';
import { CommEventUpdateComponent } from './comm-event-update.component';
import { CommEventDeleteDialogComponent } from './comm-event-delete-dialog.component';
import { commEventRoute } from './comm-event.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(commEventRoute)],
  declarations: [CommEventComponent, CommEventDetailComponent, CommEventUpdateComponent, CommEventDeleteDialogComponent],
  entryComponents: [CommEventDeleteDialogComponent],
})
export class JhipsterSampleApplicationCommEventModule {}

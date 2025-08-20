import {BookResponse} from '../../../services/models/book-response';

export interface BookAction {
  book : BookResponse;
  actionType : BookActionType;
}

export enum BookActionType {
  EDIT,
  SHARE,
  ARCHIVE,
  SHOW_DETAILS,
  BORROW,
  ADD_TO_WAITING_LIST
}

import { useState, useCallback } from 'react';
import { createGroup } from '../services/groupService';

export function useCreateGroupModal({ onGroupCreated } = {}) {
  const [isOpen, setIsOpen] = useState(false);

  const openModal = useCallback(() => setIsOpen(true), []);
  const closeModal = useCallback(() => setIsOpen(false), []);

  const handleSubmit = useCallback(
    async (groupData) => {
      try {
        const created = await createGroup(groupData);
        alert("Group created successfully!",groupData);
        onGroupCreated?.(created);
        closeModal();
      } catch (err) {
        console.error('Failed to create group:', err);
      }
    },
    [onGroupCreated, closeModal]
  );

  return { isOpen, openModal, closeModal, handleSubmit };
}
import { createClient } from '@supabase/supabase-js';

// Initialize Supabase (Store these in your .env file)
const supabaseUrl = import.meta.env.VITE_SUPABASE_URL;
const supabaseKey = import.meta.env.VITE_SUPABASE_ANON_KEY;
export const supabase = createClient(supabaseUrl, supabaseKey);

export const chatService = {
  // Fetch messages for a specific conversation
  getMessages: async (conversationId) => {
    const { data, error } = await supabase
      .from('messages')
      .select('*')
      .eq('conversation_id', conversationId)
      .order('created_at', { ascending: true });
    
    if (error) throw error;
    return data;
  },

  // Send a new message
  sendMessage: async (conversationId, senderId, text) => {
    const { data, error } = await supabase
      .from('messages')
      .insert([
        { conversation_id: conversationId, sender_id: senderId, text: text }
      ]);
      
    if (error) throw error;
    return data;
  },

  // Subscribe to new incoming messages in real-time
  subscribeToMessages: (conversationId, callback) => {
    return supabase
      .channel(`public:messages:conversation_id=eq.${conversationId}`)
      .on('postgres_changes', { 
        event: 'INSERT', 
        schema: 'public', 
        table: 'messages',
        filter: `conversation_id=eq.${conversationId}`
      }, payload => {
        callback(payload.new);
      })
      .subscribe();
  }
};
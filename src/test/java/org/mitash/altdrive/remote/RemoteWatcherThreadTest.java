package org.mitash.altdrive.remote;

import org.junit.Before;
import org.junit.Test;
import org.mitash.altdrive.drive.AltChange;
import org.mitash.altdrive.drive.AltChangeList;
import org.mitash.altdrive.drive.AltDrive;
import org.mitash.altdrive.event.Event;
import org.mitash.altdrive.event.EventPublisher;
import org.mitash.altdrive.event.Listener;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Jacob Mitash
 */
public class RemoteWatcherThreadTest {

    private RemoteWatcherThread remoteWatcherThread;
    private int events;

    @Before
    public void setUp() throws Exception {
        AltDrive altDrive = new AltDrive() {
            private int hit = 0;

            @Override
            public void initialize() throws IOException {

            }

            @Override
            public String getStartPageToken() {
                return "start page token";
            }


            @Override
            @SuppressWarnings("Duplicates")
            public AltChangeList getChanges(String pageToken) {
                if(hit == 0) {
                    assertEquals("start page token", pageToken);
                    hit++;

                    return new AltChangeList() {
                        @Override
                        public List<AltChange> getChanges() {
                            return Collections.singletonList(new AltChange() {
                                @Override
                                public String getFileId() {
                                    return "file id";
                                }

                                @Override
                                public Boolean isRemoved() {
                                    return false;
                                }
                            });
                        }

                        @Override
                        public String getNewStartPageToken() {
                            return null;
                        }

                        @Override
                        public String getNextPageToken() {
                            return "next page token";
                        }
                    };
                } else if(hit == 1) {
                    assertEquals("next page token", pageToken);
                    hit++;

                    return new AltChangeList() {
                        @Override
                        public List<AltChange> getChanges() {
                            return Collections.singletonList(new AltChange() {
                                @Override
                                public String getFileId() {
                                    return "file id";
                                }

                                @Override
                                public Boolean isRemoved() {
                                    return false;
                                }
                            });
                        }

                        @Override
                        public String getNewStartPageToken() {
                            return "new start page token";
                        }

                        @Override
                        public String getNextPageToken() {
                            return null;
                        }
                    };
                } else {
                    assertEquals("new start page token", pageToken);

                    return new AltChangeList() {
                        @Override
                        public List<AltChange> getChanges() {
                            return Collections.singletonList(new AltChange() {
                                @Override
                                public String getFileId() {
                                    return "file id";
                                }

                                @Override
                                public Boolean isRemoved() {
                                    return false;
                                }
                            });
                        }

                        @Override
                        public String getNewStartPageToken() {
                            return AltDrive.ERROR_STRING;
                        }

                        @Override
                        public String getNextPageToken() {
                            return null;
                        }
                    };
                }
            }
        };

        EventPublisher eventPublisher = new EventPublisher() {

            @Override
            public void publishEvent(Event event) {
            }

            @Override
            public void queueEvent(Event event) {
                events++;
            }

            @Override
            public Event dequeueEvent() throws InterruptedException {
                return null;
            }

            @Override
            public void addListener(Listener listener) {
            }

            @Override
            public void removeListener(Listener listener) {
            }
        };

        remoteWatcherThread = new RemoteWatcherThread(altDrive, eventPublisher, 0);
    }

    @Test
    public void run() throws Exception {
        remoteWatcherThread.run();

        assertEquals(3, events);
    }

}
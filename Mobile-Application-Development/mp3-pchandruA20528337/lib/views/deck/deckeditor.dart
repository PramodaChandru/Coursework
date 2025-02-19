import 'package:flutter/material.dart';
import 'package:mp3/models/datanotifier.dart';
import 'package:provider/provider.dart';

class DeckEditor extends StatefulWidget {
  final Decks? deck;

  const DeckEditor({Key? key, this.deck}) : super(key: key);

  @override
  DeckEditorState createState() => DeckEditorState();
}

class DeckEditorState extends State<DeckEditor> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _controller;

  @override
  void initState() {
    super.initState();
    _controller = TextEditingController(text: widget.deck?.name ?? '');
  }

  Future<void> _handleDelete(DataNotifier dataNotifier, Decks deck) async {
    if (deck.id != null) {
      await dataNotifier.deleteDeck(deck);
      if (context.mounted) {
        Navigator.of(context).pop();
      }
    }
  }

  Future<void> _handleSaveOrUpdate(
      DataNotifier dataNotifier, Decks? deck, String deckName) async {
    if (deck != null) {
      deck.name = deckName;
      await dataNotifier.updateDeck(deck);
    } else {
      final newDeck = Decks(name: deckName);
      await dataNotifier.saveDeck(newDeck);
    }
    if (context.mounted) {
      Navigator.of(context).pop();
    }
  }

  @override
  Widget build(BuildContext context) {
    final dataNotifier = Provider.of<DataNotifier>(context, listen: false);

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 27, 85, 167),
        title: const Text('Edit deck',
            style: TextStyle(
              color: Colors.white,
            )),
      ),
      body: Form(
        key: _formKey,
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextFormField(
                controller: _controller,
                decoration: const InputDecoration(
                  hintText: '',
                  labelText: 'Deck name',
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter a deck name';
                  }
                  return null;
                },
              ),
              const SizedBox(height: 16.0),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    style: ButtonStyle(
                      backgroundColor:
                          MaterialStateProperty.all(Colors.transparent),
                      shadowColor:
                          MaterialStateProperty.all(Colors.transparent),
                    ),
                    onPressed: () {
                      if (_formKey.currentState!.validate()) {
                        _handleSaveOrUpdate(
                            dataNotifier, widget.deck, _controller.text);
                      }
                    },
                    child: const Text(
                      'Save',
                      style: TextStyle(color: Color.fromARGB(255, 27, 85, 167)),
                    ),
                  ),
                  const SizedBox(
                    width: 16,
                  ),
                  if (widget.deck != null) ...[
                    ElevatedButton(
                      style: ButtonStyle(
                        backgroundColor:
                            MaterialStateProperty.all(Colors.transparent),
                        shadowColor:
                            MaterialStateProperty.all(Colors.transparent),
                      ),
                      onPressed: () {
                        _handleDelete(dataNotifier, widget.deck!);
                      },
                      child: const Text(
                        'Delete',
                        style:
                            TextStyle(color: Color.fromARGB(255, 27, 85, 167)),
                      ),
                    ),
                  ]
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
